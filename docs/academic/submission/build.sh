#!/bin/sh
set -eu
cd -- "$(dirname -- "$0")"
preview_build_dir=$(mktemp -d)
trap 'rm -rf "$preview_build_dir"' EXIT HUP INT TERM
pdflatex -interaction=nonstopmode -halt-on-error -output-directory="$preview_build_dir" main.tex
pdflatex -interaction=nonstopmode -halt-on-error -output-directory="$preview_build_dir" main.tex
cp "$preview_build_dir/main.pdf" prodtime_sbc_preview.pdf
