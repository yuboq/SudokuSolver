package com.example.sudokusolver

enum class CellBorderMux(val muxVal: Int) {
    REGULAR(0b00000),
    LEFT(0b00001),
    RIGHT(0b00010),
    TOP(0b00100),
    BOT(0b01000),
    LEFTTOP (0b00101),
    LEFTBOT (0b01001),
    RIGHTTOP(0b00110),
    RIGHTBOT(0b01010)
}