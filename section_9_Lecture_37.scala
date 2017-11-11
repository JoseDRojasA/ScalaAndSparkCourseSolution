import org.apache.spark.sql.SparkSession

var spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema", "true").csv("data/FL_insurance_sample.csv")

//bdf.head(5)
/**
for(row <-df.head(5)) {
  println(row)
}

df.columns
df.describe().show()
**/

val df2 = df.withColumn("latitudePlusLongitude", df("point_latitude") + df("point_longitude"))
df2.printSchema()

df2.select(df2("latitudePlusLongitude").as("LPL")).show()
