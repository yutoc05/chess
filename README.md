# Chess (Greenfoot)

A local multiplayer chess game implemented in Java using the Greenfoot framework.

<img width="1000" height="1000" alt="Chess game screenshot" src="https://github.com/user-attachments/assets/ad0f321f-baa6-48a3-b641-64a424ae7239" />

## Overview

This project runs on an 8x8 board (`MyWorld`) and supports turn-based play between white and black using drag-and-drop piece movement.

## Features

- Standard chess piece movement rules
- Turn enforcement (white moves first)
- Captures
- Check detection
- Castling (short and long)
- En passant
- Pawn promotion (queen, rook, bishop, knight)
- Local two-player gameplay

## Tech Stack

- Java
- Greenfoot scenario project (`project.greenfoot`)

## Project Structure

- `MyWorld.java` – board setup and initial piece placement
- `Piece.java` – shared movement, capture, and check validation logic
- `WhitePiece.java` / `BlackPiece.java` – side-specific drag/turn behavior
- `W*.java` and `B*.java` – concrete piece implementations
- `images/` – piece and tile sprites
- `project.greenfoot` – Greenfoot project configuration

## How to Run

1. Install Greenfoot.
2. Open this repository folder as a Greenfoot scenario.
3. Open `MyWorld` as the world class (if not already selected).
4. Click **Run** to start the game.

## Controls

- Click and drag a piece to a target square.
- Release to attempt the move.
- Invalid moves are automatically reverted.
