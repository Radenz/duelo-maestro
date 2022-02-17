<div id="top"></div>
<br />
<div align="center">
  <a href="https://github.com/Radenz/duelo-maestro">
    <img src="logo.png" alt="Logo" width="365" height="278">
  </a>

<h3 align="center">Duelo Maestro</h3>

  <p align="center">
    An Overdrive game simple bot built in Java powered by greedy algorithm.
    <br />
    <a href="https://youtu.be/EzPI2PUhXZk">View Demo</a>
    ·
    <a href="https://duelo-maestro-docs.web.app/">Documentation</a>
  </p>
</div>

#### Table of Contents
- [About The Project](#about-the-project)
- [How It Works](#how-it-works)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
    - [Building using IntelliJ](#building-using-intellij)
    - [Building using Maven](#building-using-maven)
- [Usage](#usage)
- [Contributors](#contributors)

## About The Project

Duelo Maestro is a simple bot for [Overdrive](https://github.com/EntelectChallenge/2020-Overdrive)
game written in Java. This bot uses greedy algorithm concepts for its logic and main decision-making
process. There are currently 10 different built-in strategies implemented within Duelo Maestro and it is easy
to expand. Duelo Maestro is made with simple APIs and highly configurable actions, thus makes
it really easy to implement custom strategies. This project is made to fulfill
`Tugas Besar 1 IF2211 Strategi Algoritma`.

## How It Works
Duelo Maestro bot uses greedy algorithm to make decision. Basically, every
`Strategy` implemented inside has its own `Action` list. When run, the bot will
evaluate whether those `Action` are feasible, i.e. reasonable to do, or not. The order of
`Action` to evaluate is based on the priorities of actions that a `Strategy` has.
A `Strategy` may or may not include an `Action` in its `Action` list, but the `Action`
list must be ordered descending by `Action` priorities, hence no multiple `Action`s
have the same priority. Upon reaching a feasible (reasonable) `Action`, the bot will
stop evaluating remaining `Action`s in the `Action` list and will execute the
feasible `Action`. When executed, an action will return a `Command` to be returned
to the game engine.

## Getting Started

### Prerequisites

You will need [Java](https://www.java.com/en/download/) and JDK 17 which can
be downloaded [here](https://www.oracle.com/java/technologies/downloads/). You
will also need [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) or
[Maven](https://maven.apache.org/download.cgi) to build the bot.

### Installation

#### Building using IntelliJ
1. Open the repository root directory as IntelliJ IDEA project.
2. Expand `Maven` sidebar on the right side.
3. Expand `java-starter-bot > Lifecycle` and choose `install`.
4. Wait until the building process is finished.

#### Building using Maven
1. Run
```
mvn install
```
2. Wait until the building process is finished.

## Usage
After building the bot, there will be a newly created `target` directory that
contains `java-starter-bot-jar-with-dependencies.jar` file. You can either copy
this jar file to your own bot directory or just point either player (`player-a` or
`player-b`) int the game configuration file (`game-runner-config.json`) to the
root directory of the repository.

## Contributors
This project is made by:
- [Farnas Rozaan Iraqee](https://github.com/OjaanIr/) (student id 13520067)
- [Raden Rifqi Rahman](https://github.com/Radenz/) (student id 13520166)