package io.fajarca.core.common

abstract class Mapper<Input, Output> {
    abstract fun map(input : Input) :  Output
}