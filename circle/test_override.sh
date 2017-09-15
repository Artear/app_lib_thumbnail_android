#!/bin/bash

function description {
	echo "+------------------------------------------+"
	echo "|               CONFIG NODE                |"
	echo "+------------------------------------------+"
	echo " NODE TOTAL   : $CIRCLE_NODE_TOTAL"
	echo " NODE INDEX   : $CIRCLE_NODE_INDEX"
	echo "+------------------------------------------+"
	echo ""
}

function gradlew_assemble {
	echo "+------------------------------------------+"
	echo "- Run Gradlew Assemble"
	echo "+------------------------------------------+"
	./gradlew assemble
}

function gradlew_test {
	echo "+------------------------------------------+"
	echo "- Run Gradlew Test"
	echo "+------------------------------------------+"
	./gradlew test
}

description
if (($CIRCLE_NODE_TOTAL >= 2 )); then
	if [[ $CIRCLE_NODE_INDEX == 0 ]]; then
		gradlew_assemble
	fi

	if [[ $CIRCLE_NODE_INDEX == 1 ]]; then
		gradlew_test
	fi
else
	gradlew_test
	gradlew_assemble
fi