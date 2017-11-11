// DATAFRAME PROJECT
// Use the Netflix_2011_2016.csv file to Answer and complete
// the commented tasks below!

// Start a simple Spark Session
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().getOrCreate()
// Load the Netflix Stock CSV File, have Spark infer the data types.
val df = spark.read.option("header", "true").option("inferSchema", "true").csv("data/Netflix_2011_2016.csv")
df.show()
// What are the column names?
df.columns
// What does the Schema look like?
df.printSchema()
// Print out the first 5 columns.
for (i <- 0 until 5) {
  println(columns(i))
}
// Use describe() to learn about the DataFrame.
df.describe().show()
// Create a new dataframe with a column called HV Ratio that
// is the ratio of the High Price versus volume of stock traded
// for a day.
val df2 = df.withColumn("HV Ratio", df("High")/df("Volume"))
// What day had the Peak High in Price?
df2.select(df2("Date")).orderBy($"High".desc).show(1)
// What is the mean of the Close column?
df2.select(mean("Close")).show()
// What is the max and min of the Volume column?
df2.select(max("Volume")).show()
df2.select(min("Volume")).show()
// For Scala/Spark $ Syntax
import spark.implicits._
// How many days was the Close lower than $ 600?
println(df2.filter($"Close" < 600).count())
// What percentage of the time was the High greater than $500 ?
(df2.filter($"High" > 500).count()*1.0/df2.count())*100
// What is the Pearson correlation between High and Volume?
df2.select(corr("High", "Volume")).show()
// What is the max High per year
df2.groupBy(year(df2("Date"))).max("High").orderBy("year(Date)").show()
// What is the average Close for each Calender Month?
val df3 = df2.withColumn("Month", month(df("Date")))
val df4 = df3.select($"Month", $"Close").groupBy("Month").mean()
df4.select($"Month", $"avg(Close)").orderBy("Month").show()
