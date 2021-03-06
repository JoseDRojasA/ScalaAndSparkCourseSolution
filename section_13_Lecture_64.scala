import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit}
import org.apache.spark.sql.types.DoubleType

import org.apache.log4j._

Logger.getLogger("org").setLevel(Level.ERROR)

import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

var data = spark.read.option("header", "true").option("inferSchema", "true").format("csv").load("data/USA_Housing.csv")

// Assembler doesn't accept string so with cast thouse columns to double.
data = data.withColumn("Avg Area Income", data("Avg Area Income").cast(DoubleType)).withColumn("Avg Area House Age", data("Avg Area House Age").cast(DoubleType))
data = data.na.drop()

import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

val df = (data.select(data("Price").as("label"), $"Avg Area Income", $"Avg Area House Age", $"Avg Area Number of Rooms", $"Area Population"))

val assembler = new VectorAssembler().setInputCols(Array("Avg Area Income", "Avg Area House Age", "Avg Area Number of Rooms", "Area Population")).setOutputCol("features")

val output = assembler.transform(df).select($"label",$"features")

val Array(training, test) = output.select("label", "features").randomSplit(Array(0.7, 0.3), seed=12345)

val lr = new LinearRegression()

val paramGrid = new ParamGridBuilder().addGrid(lr.regParam, Array(1000000000, 0.001)).build()

val trainvalsplit = (new TrainValidationSplit()
                        .setEstimator(lr)
                        .setEvaluator(new RegressionEvaluator().setMetricName("r2"))
                        .setEstimatorParamMaps(paramGrid)
                        .setTrainRatio(0.8)
                        )
val model = trainvalsplit.fit(training)

model.transform(test).select("features", "label", "prediction").show()