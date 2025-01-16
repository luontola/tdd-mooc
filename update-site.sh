#!/bin/bash
set -euxo pipefail

lein clean
git worktree prune
git worktree add --no-checkout target/dist gh-pages

gitfile=$(cat target/dist/.git)
lein export
echo "$gitfile" > target/dist/.git

(
    cd target/dist
    echo tdd.mooc.fi > CNAME
    git add -A .
    git commit -m "Update site"
    git push
)
