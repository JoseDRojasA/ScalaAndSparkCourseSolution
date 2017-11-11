import org.apache.spark.sql.SparkSession

var spark = SparkSession.builder().getOrCreate()

val df = spark.read.option("header", "true").option("inferSchema", "true").csv("data/CitiGroup2006_2008")
/**
df.printSchema()
print("Month")
df.select(month(df("Date"))).show()
print("Year")
df.select(year(df("Date"))).show()
df.show()
**/
/**
  Return average per year
**/

val df2 = df.withColumn("Year", year(df("Date")))
val dfavgs = df2.groupBy("Year").mean()

//dfavgs.select($"Year", $"avg(Close)").orderBy("Year").show()

val dfmins = df2.groupBy("Year").min()

dfmins.select($"Year", $"min(Close)").show()
