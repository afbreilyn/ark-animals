#!/usr/bin/env bash

function set_bash_fail_on_error() {
    set -e
}

function go_to_root_directory() {
    root_directory=$(git rev-parse --show-toplevel)

    cd "$root_directory"
}

function run_tests() {
  run_gradle_tests
}

function run_gradle_tests() {
    ./gradlew test
}

function push_code() {
    git push
}

function run_linter() {
  npm --prefix=web run lint
}

function main() {
    set_bash_fail_on_error
    go_to_root_directory

    run_linter

    run_tests

    push_code
}

main "$@"
