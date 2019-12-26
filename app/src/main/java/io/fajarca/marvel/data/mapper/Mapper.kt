package io.fajarca.marvel.data.mapper

abstract class Mapper<Input, Output> {
    abstract fun map(input : Input) :  Output
}