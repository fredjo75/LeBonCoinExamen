package com.example.leboncoinexamen.data.oop.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}