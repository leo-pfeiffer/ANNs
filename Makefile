SRC_FILES := $(shell find ./src/* -name '*.java')
MINET_FILES := $(shell find ./minet/* -name '*.java')
TEST_FILES := $(shell find ./test/* -name '*java')
EVAL_FILES := $(shell find ./evaluation/* -name '*java')
LIB := ./lib/*
CLASSPATH := $(LIB):src:test:evaluation:minet


.PHONY: compile
# Compile all java files
compile:
	@javac -cp $(LIB):. $(SRC_FILES) $(TEST_FILES) $(EVAL_FILES) $(MINET_FILES)

.PHONY: clean
# Remove java class files
clean:
	find ./src/* -name '*.class' -delete
	find ./test/* -name '*.class' -delete
	find ./evaluation/* -name '*.class' -delete
	find ./minet/* -name '*.class' -delete


.PHONY: test
# Run JUnit tests
test:
	@java -cp $(CLASSPATH):. org.junit.runner.JUnitCore test.TestSuite


.PHONY: evaluation
# Run the evaluation script
evaluation:
	@python3 evaluation/evaluation.py


.PHONY: tuning
# Run the hyper parameter tuning script
tuning:
	@java -cp $(CLASSPATH):. evaluation.HyperParamTuning


.PHONY: timing
# Run the timing script
timing:
	@java -cp $(CLASSPATH):. evaluation.Timer


.PHONY: help
# Found here: https://stackoverflow.com/a/35730928/12168211
# Print available commands
help:
	@echo "=============================================================="
	@echo "Available Make commands ======================================"
	@echo "=============================================================="
	@awk '/^#/{c=substr($$0,3);next}c&&/^[[:alpha:]][[:alnum:]_-]+:/{print substr($$1,1,index($$1,":")),c}1{c=0}' $(MAKEFILE_LIST) | column -s: -t