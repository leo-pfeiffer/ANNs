# CS5011

## Report

The report can be found in `doc/report.pdf`.

## Running the project

Running instructions for the models are provided in `run.sh`.

To run the unit tests, word2vec scripts and the evaluation, please refer to
the commands provided in the Makefile (see below) and the report.

## Project structure

```
+ data:         Data for models
+ evaluation:   Evaluation scripts
+ lib:          Dependencies (for unit testing and evaluation)
+ minet:        From starter code
+ src:          Source code
+ test:         Unit tests
```

## Makefile

The following commands are available from the root directory of the project
and can be called using the make command.

Make sure to compile (`make compile`) the project before using any of the other commands.

```
compile      Compile all java files
clean        Remove java class files
test         Run JUnit tests
evaluation   Run the evaluation script
tuning       Run the hyper parameter tuning script
timing       Run the timing script
help         Print available commands
```
