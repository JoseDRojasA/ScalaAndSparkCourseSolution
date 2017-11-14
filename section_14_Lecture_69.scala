import org.apache.spark.sql.SparkSession

import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()

val data = spark.read.option("header", "true").option("inferSchema", "true").format("csv").load("data/Wholesale customers data.csv")

val feature_data = data.select($"Fresh", $"Milk", $"Grocery", $"Frozen", $"Detergents_Paper", $"Delicassen")

import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

val assembler = new VectorAssembler().setInputCols(Array(
    "Fresh", "Milk", "Grocery", "Frozen", "Detergents_Paper", "Delicassen"
)).setOutputCol("features")

val training_data = assembler.transform(feature_data).select("features")

import org.apache.spark.ml.clustering.KMeans 

val kmeans = new KMeans().setK(30)

val model = kmeans.fit(training_data)

val WSSSE = model.computeCost(training_data)

println(s"Within Set Sum of Squeared Errors = $WSSSE")