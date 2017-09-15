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

function copy_test_reports {
    echo "+------------------------------------------+"
	echo "- Copy Test Reports"
	echo "+------------------------------------------+"
	#cp -r /home/ubuntu/tn_2016_android/app/build/test-results/release $CIRCLE_TEST_REPORTS
}

function copy_apks {
	echo "+------------------------------------------+"
	echo "- Copy APKs"
	echo "+------------------------------------------+"
	#cp -r /home/ubuntu/tn_2016_android/app/build/outputs/apk/ $CIRCLE_ARTIFACTS
}

description

if (($CIRCLE_NODE_TOTAL >= 2 )); then
	if [[ $CIRCLE_NODE_INDEX == 0 ]]; then
		copy_apks
	fi

	if [[ $CIRCLE_NODE_INDEX == 1 ]]; then
		copy_test_reports
	fi
else
	copy_test_reports
	copy_apks
fi