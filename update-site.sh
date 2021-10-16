#!/bin/bash
set -euxo pipefail

npm run clean
git worktree prune
git worktree add --no-checkout public gh-pages

npm run build

(
    cd public
    echo tdd.mooc.fi > CNAME
    git add -A .
    git commit -m "Update site"
    git push
)
