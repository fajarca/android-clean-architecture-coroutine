package io.fajarca.feature.data.mapper

abstract class Mapper<Input, Output> {
    abstract fun map(input : Input) :  Output
}