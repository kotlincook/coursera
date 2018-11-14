package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        allDrivers.filterNot { driver ->
            trips.any { it.driver == driver }
        }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter { passenger ->
            trips.count { passenger in it.passengers } >= minTrips
        }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.filter { passenger ->
            trips.filter {
                it.driver == driver && passenger in it.passengers
            }.count() > 1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.filter { passenger ->
            trips.count { passenger in it.passengers && it.discount != null } >
                    trips.count { passenger in it.passengers && it.discount == null } }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val groupIdx = trips.groupBy { it.duration / 10 }.maxBy { it.value.size }?.key
    return if (groupIdx == null) null else (10 * groupIdx)..(10 * groupIdx + 9)
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val driverToIncome = trips
            .groupBy { it.driver }
            .mapValues { it.value.sumByDouble { it.cost } }
            .toList()
            .sortedBy { -it.second }
    val totalIncome = driverToIncome.sumByDouble { it.second }
    if (driverToIncome.isEmpty()) return false
    val driverToIncome20Percent = driverToIncome.subList(0, Math.min(allDrivers.size/5, driverToIncome.size))
    val incomeOfBest20Percent = driverToIncome20Percent.sumByDouble { it.second }
    return incomeOfBest20Percent >= 0.8 * totalIncome
}