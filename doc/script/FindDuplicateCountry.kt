val data = CURRENCY_DATA.flatMap { it.countries }.sortedBy { it.code }

data.groupBy { it.code }.filter { it.value.size > 1 }.forEach {
    println(it.value.size.toString() + " " + it)
}
