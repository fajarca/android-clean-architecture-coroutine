package io.fajarca.core.mapper

abstract class Mapper<in I, out O>{
    abstract suspend fun map(input : I) :  O
}