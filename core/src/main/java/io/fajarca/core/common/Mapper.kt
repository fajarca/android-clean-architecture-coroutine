package io.fajarca.core.common

abstract class Mapper<in I, out O> {
    abstract fun map(input : I) :  O
}