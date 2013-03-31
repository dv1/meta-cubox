OpenEmbedded BSP layer for SolidRun's CuBox 
===========================================

This layer provides UNOFFICIAL support for SolidRun's CuBox platform for use
with OpenEmbedded and/or Yocto. See the [CuBox homepage](http://solid-run.com/cubox)
for details about this platform.



Dependencies
============

* URI: git://git.openembedded.org/openembedded-core
* branch: master
* revision: HEAD



Building the meta-cubox layer
=============================

To build a BSP for the CuBox, first set up your OE-core distribution,
then add to bblayers.conf the path to your local copy of meta-cubox.

Then, add to local.conf:

    MACHINE ?= "cubox"

This layer adds two values to the list of available tunes:

* `marvellpj4` (for softfp builds)
* `marvellpj4hf` (for hardfp builds)

These add compiler flags for Marvell's PJ4 processor, and are based on the armv7a
tunes. `marvellpj4hf` is used by default. To explicitely set one of these, add
it to the local conf. Here is an example of a line in local.conf for softfp builds:

    DEFAULTTUNE ?= "marvellpj4"

For the rest, follow the building guidelines of the distro of your choice.


Demo images
-----------

Two demo images are included:

* cubox-demo-image-x11 : minimal image with X11 and a fullscreen matchbox-terminal
* cubox-demo-image-sato : image with the sato desktop

Both images include EGL/OpenGLES/OpenVG binaries, the hardware-accelerated video
codecs, all of the GStreamer plugins from -base-meta, -good-meta, -bad-meta, and
the GStreamer plugins for the Marvell codecs and enhanced XVideo image sink.
In addition, they also contain plugins from -ugly-meta, gst-ffmpeg, gst-fluendo-mp3,
and gst-fluendo-mpegdemux, if `commercial` was added to the `LICENSE_FLAGS_WHITELIST`
variable. The easiest way how to do that is to add this line to local.conf:

    LICENSE_FLAGS_WHITELIST += "commercial"



Layer structure
===============

This section describes various places of interest in this layer. It contains additional
information, and does not have to be read for just using meta-cubox. For anyone who
wants to customize this layer, or just understand some details about it and the CuBox,
this section is of interest.

Various recipes pull sources and binaries from a big package,
[cubox-packages.tar.gz](http://download.solid-run.com/pub/solidrun/cubox/packages/cubox-packages-source/cubox-packages.tar.gz).
For sake of clarity, it is just referred to as "cubox-packages tarball" in the following
subsections.


The boot.scr
------------

The CuBox comes with an already flashed U-Boot that tries to find a boot.scr . It
first tries to find it on various types of media (USB stick, SD card, storage device
connected over Serial ATA ..), then tries to find it over the network. To facilitate
deployment on media, the demo image recipes first produce a boot.scr out of a
boot.script file that is located in config/boot/ , and copies this boot.scr into the
image, into the boot/ directory. It also copies the kernel uImage into this same
directory. As a result, all that needs to be done is to unpack the generated rootfs
tarball on the used media (for example, an SD card), and the CuBox should boot with it.


The kernel
----------

Currently, [rabeeh's 3.6.9 fork](https://github.com/rabeeh/linux) is used. Eventually,
CuBox support might be mainlined. Until then, this fork works just fine. The included
`cubox_defconfig` is used for kernel configuration.


EGL / OpenGLES / OpenVG
-----------------------

Support for EGL, OpenGL ES, and OpenVG is provided by the Vivante binaries supplied
by Marvell. Unfortunately, there are multiple variants of these, which can cause
a lot of confusion.

The zipped binaries can be found [on the solid-run website](http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-opengl/).
The -light variants are used for the binaries. C headers for EGL etc. are retrieved
from [this zip archive](http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-libgfx/marvell-libgfx-headers-20120713.tar.bz2).
These are the newest headers and binaries. Other ones are either outdated or broken.

Also, since the kernel already links in the GALcore component, the `galcore.ko`
kernel module from these packages is not used or installed.


Hardware-accelerated video decoding using Marvell's vMeta engine
----------------------------------------------------------------

vMeta is supported in this layer. It is split into an open-source library (libvmeta)
and closed-source libraries (libmiscgen, the HAL, and the codecs).
libvmeta is extracted from the cubox-packages tarball.

In softfp builds, the closed-source libraries are extracted from the marvell-ipp tarball
contained within the cubox-packages one. In hardfp builds, closed-source libraries are
extracted from [vmeta-hardfp.tar.xz](http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-vmeta/vmeta-hardfp.tar.xz)
and from [libmiscgen-hardfp.tar.xz](http://download.solid-run.com/pub/solidrun/cubox/packages/marvell-vmeta/libmiscgen-hardfp.tar.xz).

There are two groups of provided closed-source codecs:

* vMeta decoder: hardware-accelerated video decoding, supporting h.264, WMV,
  MPEG2, MPEG4, and more
* IPP codecs: implemented purely in software, these codecs make use of the
  Marvell PJ4's iWMMXt instruction set.
  No hardfp variants of these codecs have been made available yet.
  There are IPP for both audio (AAC, AMR, MP3 etc.) and video bitstreams
  (h.264, WMV, MPEG2, MPEG2, etc.).


X.org Dovefb driver
-------------------

This driver enables X11 support for the Dove platform. Since its initial release,
Jon Nettleton has added numerous improvements. This improved driver can be found
[on the dev.laptop.org git site](http://dev.laptop.org/git/users/jnettlet/xf86-video-dove/)
and is used in this layer. Currently, EXA is disabled by default in the xorg.conf
due to instabilities. XVideo has been successfully tested.


GStreamer plugins
-----------------

GStreamer plugins for both the vMeta/IPP codecs and the BMM enhanced
xvimagesink are supported. The IPP codec plugins are built only if softfp is used.
The bmmxvimagesink (BMM = Buffer Management Module) is built regardless of what ABI
is used.
