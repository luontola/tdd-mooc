#!/usr/bin/env node
import "source-map-support/register"
import * as cdk from "aws-cdk-lib"
import { UploadBucketStack } from "../lib/upload-bucket-stack"

const app = new cdk.App()
new UploadBucketStack(app, "UploadBucketStack", {
  env: { account: "344906152169", region: "eu-north-1" },
})
