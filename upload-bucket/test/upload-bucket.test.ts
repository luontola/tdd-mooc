// import * as cdk from 'aws-cdk-lib';
// import { Template } from 'aws-cdk-lib/assertions';
// import * as UploadBucket from '../lib/upload-bucket-stack';
import { execSync } from "child_process"

// example test. To run these tests, uncomment this file along with the
// example resource in lib/upload-bucket-stack.ts
//test('SQS Queue Created', () => {
//   const app = new cdk.App();
//     // WHEN
//   const stack = new UploadBucket.UploadBucketStack(app, 'MyTestStack');
//     // THEN
//   const template = Template.fromStack(stack);

//   template.hasResourceProperties('AWS::SQS::Queue', {
//     VisibilityTimeout: 300
//   });
//})

describe("Uploading files", () => {
  let filename = ""
  beforeAll(() => {
    filename = `test-${Date.now()}.txt`
  })

  test("Anonymous users can upload files", async () => {
    const output = execSync(`aws s3 cp test/hello.txt  s3://tdd-mooc-exercises/${filename} --no-sign-request`).toString()
    expect(output).toContain("upload: test/hello.txt to s3://")
  })

  test.todo("Anonymous users cannot overwrite existing files")

  test.todo("Anonymous users cannot download files")

  test.todo("Bucket owner can download files")
})
