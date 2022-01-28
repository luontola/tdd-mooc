import * as cdk from "aws-cdk-lib"
import * as s3 from "aws-cdk-lib/aws-s3"
import * as iam from "aws-cdk-lib/aws-iam"

export class UploadBucketStack extends cdk.Stack {
  constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
    super(scope, id, props)

    const bucket = new s3.Bucket(this, "UploadBucket", {
      bucketName: "tdd-mooc-exercises",
      versioned: true,
      enforceSSL: true,
    })

    // https://prettyplease.me/anonymous-s3-upload-with-full-owner-control/
    // https://stackoverflow.com/questions/60310575/how-to-add-s3-bucketpolicy-with-aws-cdk
    const allowAnonymousUpload = new iam.PolicyStatement({
      actions: ["s3:PutObject"],
      resources: [bucket.arnForObjects("*")],
      principals: [new iam.AnyPrincipal()],
    })
    bucket.addToResourcePolicy(allowAnonymousUpload)
  }
}
