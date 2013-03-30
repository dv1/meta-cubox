DESCRIPTION = "Image with Sato, a mobile environment and visual style for \
mobile devices. The image supports X11 with a Sato theme, Pimlico \
applications, and contains terminal, editor, and file manager. In addition, \
it has GStreamer and 3D and video decoding acceleration drivers installed."

include cubox-demo-image.inc

IMAGE_FEATURES += "splash x11-sato"

IMAGE_INSTALL += "packagegroup-core-x11-sato-games"
