#!/bin/sh

# Zyro Browser icon generation
# Uses AppLogo.png (project root) as the source for all launcher icons.
# AppLogo.png is resized/composited to match the target icon dimensions.
# Requires: imagemagick

logo=$(dirname "$0")/../AppLogo.png
w=$(identify -format %w "$1")

case $(basename "$1") in
  layered_app_icon_background*)
    # Background layer: solid white (or transparent) fill; logo is on foreground layer
    convert -size "${w}x${w}" xc:white "$1"
    echo "$1 (${w}px background layer)"
    ;;
  layered_app_icon*)
    # Foreground layer: AppLogo.png centered, scaled to 80% of icon area
    fw=$(echo "$w * 80 / 100" | bc)
    convert "$logo" \
      -resize "${fw}x${fw}" \
      -background transparent \
      -gravity center \
      -extent "${w}x${w}" \
      "$1"
    echo "$1 (${w}px foreground layer from AppLogo.png)"
    ;;
  *)
    # Standard icon (app_icon, notification icon, etc.)
    # Fit AppLogo.png into a square canvas at exact target size
    convert "$logo" \
      -resize "${w}x${w}" \
      -background transparent \
      -gravity center \
      -extent "${w}x${w}" \
      "$1"
    echo "$1 (${w}px from AppLogo.png)"
    ;;
esac
