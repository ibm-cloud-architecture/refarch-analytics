# Customer Churn analysis
The notebooks in this folder are here to address one of the common use case for companies on competitive market like telecom providers: How to predict if a customer would churn?

They are based on the following source: https://github.com/IBMDataScience/DSX-DemoCenter/tree/master/predictCustomerChurn but we have tuned it to integrate to internal datasources and deliver more explanation for beginner data scientists or a software engineer who wants to understand what's going on.

## Pre-requisite
To run those notebooks on DSX on ICP you need to have:
* Have an ICP installed up and running: We have configured a 5 hosts topology.
* Define a namespace for ICP. We used the `greencompute` namespace.
* Deploy the DSX enterprise edition on ICP. See [this note](../../docs/ICP/README.md) on how to do that.
* Clone this repository.
* Create an instance of DB2 warehouse on ICP, see note here to understand how it is done.

## Step 1- Upload data to Db2 Warehouse.

## Step 2- Load the customer churn analysis notebook in dsx

## Step 3 - Execute the notebook step by Step

## Step 4 - Deploy the model as a spark 
