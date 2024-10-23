import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression
import java.io.File

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation.*

data class StockData(
    val date: String,
    val excess_return: Double,
    val mkt_rf: Double,
    val smb: Double,
    val hml: Double
)

fun main() {
    val data = readCsvFile("C:\\dev\\CAPM-FF3-Carhart4-Models\\statistical-models\\src\\main\\resources\\Coursework_Data_24-25.csv")

    // Prepare data for regression
    val y = data.map { it.excess_return }.toDoubleArray()
    val x = Array(data.size) { i ->
        doubleArrayOf(data[i].mkt_rf, data[i].smb, data[i].hml)
    }

    // Perform regression
    val regression = OLSMultipleLinearRegression()
    regression.newSampleData(y, x)

    // Get coefficients
    val coefficients = regression.estimateRegressionParameters()
    val intercept = coefficients[0]
    val betas = coefficients.sliceArray(1..coefficients.lastIndex)

    // Calculate predicted excess returns
    val predictedReturns = x.map { observation ->
        intercept + observation.zip(betas).sumOf { (x, beta) -> x * beta }
    }

    println("Intercept (Alpha): ${coefficients[0]}")
    println("Market Beta: ${coefficients[1]}")
    println("SMB coefficient: ${coefficients[2]}")
    println("HML coefficient: ${coefficients[3]}")

    // Calculate R-squared
    val rSquared = regression.calculateRSquared()
    println("R-squared: $rSquared")
}

fun readCsvFile(filename: String): List<StockData> {
    val file = File(filename)
    return file.readLines().drop(1).map { line ->
        val parts = line.split(",")
        StockData(
            date = parts[0].substring(0, 4),
            excess_return = parts[5].toDouble(),
            mkt_rf = parts[10].toDouble(),
            smb = parts[11].toDouble(),
            hml = parts[12].toDouble()
        )
    }
}
