import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()
var df = spark.read.option("header", "true").option("inferSchema", "true").csv("data/CitiGroup2006_2008")

df.printSchema()

// Filtering using Scala Syntax

import spark.implicits._

// df.filter($"Close" > 480).show()
// df.filter($"Close" < 480 && $"High" < 480).show()
// df.filter($"Close" < 480 && $"High" < 480).show()
// var CH_low = df.filter($"Close" < 480 && $"High" < 480).collect()
// var CH_low = df.filter($"Close" < 480 && $"High" < 480).count()
// df.filter($"High" === 484.40).show()
// df.filter("High = 484.40").show()

df.select(corr("High", "Low")).show()
