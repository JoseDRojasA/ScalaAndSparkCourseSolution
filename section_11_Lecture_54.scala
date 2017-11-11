import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression

import org.apache.log4j._

Logger.getLogger("org").setLevel(Level.ERROR)

val spark = SparkSession.builder().getOrCreate()

val data = spark.read.option("header", "true").option("inferSchema", "true").format("csv").load("data/Clean-Ecommerce.csv")
// val data = spark.read.option("header", "true").option("inferSchema", "true").format("csv").load("data/Ecommerce Customers")


import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

var df = (data.select(data("Yearly Amount Spent").as("label"),
            $"Avg Session Length", $"Time on App", $"Time on Website",
            $"Length of Membership"))
/* Remove records with all the fields in null and labels in null*/
// df = df.na.drop(1)
val assembler = (new VectorAssembler().setInputCols(cols)
                .setOutputCol("features")
            )

val output = assembler.transform(df).select($"label", $"features")

val lr = new LinearRegression()

val lrModel = lr.fit(output)

val trainingSummary = lrModel.summary

trainingSummary.residuals.show()
trainingSummary.r2