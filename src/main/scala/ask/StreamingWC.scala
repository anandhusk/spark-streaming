package ask

object StreamingWC {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local[3]")
      .appName("streamer")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val lineDf = spark
      .readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", "9999")
      .load()

    val wordDf = lineDf
      .select(expr("explode(split(value, ' ') as word"))

    val countDf = wordDf.groupBy("word").count()

    val wordCountQuery: StreamingQuery = countDf
      .writeStream
      .format("console")
      .option("checkpointLocation", "chkpt-dr")
      .outputMode("complete")
      .start()

    wordCountQuery.awaitTermination()
  }

}
