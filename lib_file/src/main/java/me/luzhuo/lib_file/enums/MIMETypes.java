/* Copyright 2021 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_file.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 资源文件的 MIME types
 * @Author: Luzhuo
 * @Creation Date: 2021/11/13 11:41
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class MIMETypes {
    private Map<String/* suffix */, String/* mime_type */> mimeTypes = new HashMap<>();

    {
        mimeTypes.put(".xpm",   "image/x-xpixmap");
        mimeTypes.put(".7z",    "application/x-7z-compressed");
        mimeTypes.put(".zip",   "application/zip");
        mimeTypes.put(".xlsx",  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mimeTypes.put(".docx",  "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeTypes.put(".pptx",  "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mimeTypes.put(".epub",  "application/epub+zip");
        mimeTypes.put(".jar",   "application/jar");
        mimeTypes.put(".odt",   "application/vnd.oasis.opendocument.text");
        mimeTypes.put(".ott",   "application/vnd.oasis.opendocument.text-template");
        mimeTypes.put(".ods",   "application/vnd.oasis.opendocument.spreadsheet");
        mimeTypes.put(".ots",   "application/vnd.oasis.opendocument.spreadsheet-template");
        mimeTypes.put(".odp",   "application/vnd.oasis.opendocument.presentation");
        mimeTypes.put(".otp",   "application/vnd.oasis.opendocument.presentation-template");
        mimeTypes.put(".odg",   "application/vnd.oasis.opendocument.graphics");
        mimeTypes.put(".otg",   "application/vnd.oasis.opendocument.graphics-template");
        mimeTypes.put(".odf",   "application/vnd.oasis.opendocument.formula");
        mimeTypes.put(".odc",   "application/vnd.oasis.opendocument.chart");
        mimeTypes.put(".sxc",   "application/vnd.sun.xml.calc");
        mimeTypes.put(".pdf",   "application/pdf");
        mimeTypes.put(".fdf",   "application/vnd.fdf");
        mimeTypes.put(".aaf",   "application/octet-stream");
        mimeTypes.put(".msg",   "application/vnd.ms-outlook");
        mimeTypes.put(".xls",   "application/vnd.ms-excel");
        mimeTypes.put(".pub",   "application/vnd.ms-publisher");
        mimeTypes.put(".ppt",   "application/vnd.ms-powerpoint");
        mimeTypes.put(".doc",   "application/msword");
        mimeTypes.put(".ps",    "application/postscript");
        mimeTypes.put(".psd",   "image/vnd.adobe.photoshop");
        mimeTypes.put(".p7s",   "application/pkcs7-signature");
        mimeTypes.put(".ogg",   "application/ogg");
        mimeTypes.put(".oga",   "audio/ogg");
        mimeTypes.put(".ogv",   "video/ogg");
        mimeTypes.put(".png",   "image/png");
        mimeTypes.put(".jpg",   "image/jpeg");
        mimeTypes.put(".jpeg",  "image/jpeg");
        mimeTypes.put(".jxl",   "image/jxl");
        mimeTypes.put(".jp2",   "image/jp2");
        mimeTypes.put(".jpf",   "image/jpx");
        mimeTypes.put(".jpm",   "image/jpm");
        mimeTypes.put(".gif",   "image/gif");
        mimeTypes.put(".webp",  "image/webp");
        mimeTypes.put(".exe",   "application/vnd.microsoft.portable-executable");
        mimeTypes.put(".so",    "application/x-sharedlib");
        mimeTypes.put(".a",     "application/x-archive");
        mimeTypes.put(".deb",   "application/vnd.debian.binary-package");
        mimeTypes.put(".tar",   "application/x-tar");
        mimeTypes.put(".xar",   "application/x-xar");
        mimeTypes.put(".bz2",   "application/x-bzip2");
        mimeTypes.put(".fits",  "application/fits");
        mimeTypes.put(".tiff",  "image/tiff");
        mimeTypes.put(".bmp",   "image/bmp");
        mimeTypes.put(".ico",   "image/x-icon");
        mimeTypes.put(".mp3",   "audio/mpeg");
        mimeTypes.put(".flac",  "audio/flac");
        mimeTypes.put(".midi",  "audio/midi");
        mimeTypes.put(".ape",   "audio/ape");
        mimeTypes.put(".mpc",   "audio/musepack");
        mimeTypes.put(".amr",   "audio/amr");
        mimeTypes.put(".wav",   "audio/wav");
        mimeTypes.put(".aiff",  "audio/aiff");
        mimeTypes.put(".au",    "audio/basic");
        mimeTypes.put(".mpeg",  "video/mpeg");
        mimeTypes.put(".mov",   "video/quicktime");
        mimeTypes.put(".mqv",   "video/quicktime");
        mimeTypes.put(".mp4",   "video/mp4");
        mimeTypes.put(".webm",  "video/webm");
        mimeTypes.put(".3gp",   "video/3gpp");
        mimeTypes.put(".3g2",   "video/3gpp2");
        mimeTypes.put(".avi",   "video/x-msvideo");
        mimeTypes.put(".flv",   "video/x-flv");
        mimeTypes.put(".mkv",   "video/x-matroska");
        mimeTypes.put(".asf",   "video/x-ms-asf");
        mimeTypes.put(".aac",   "audio/aac");
        mimeTypes.put(".voc",   "audio/x-unknown");
        mimeTypes.put(".m4a",   "audio/x-m4a");
        mimeTypes.put(".m3u",   "application/vnd.apple.mpegurl");
        mimeTypes.put(".m4v",   "video/x-m4v");
        mimeTypes.put(".rmvb",  "application/vnd.rn-realmedia-vbr");
        mimeTypes.put(".gz",    "application/gzip");
        mimeTypes.put(".class", "application/x-java-applet");
        mimeTypes.put(".swf",   "application/x-shockwave-flash");
        mimeTypes.put(".crx",   "application/x-chrome-extension");
        mimeTypes.put(".ttf",   "font/ttf");
        mimeTypes.put(".woff",  "font/woff");
        mimeTypes.put(".woff2", "font/woff2");
        mimeTypes.put(".otf",   "font/otf");
        mimeTypes.put(".eot",   "application/vnd.ms-fontobject");
        mimeTypes.put(".wasm",  "application/wasm");
        mimeTypes.put(".shx",   "application/octet-stream");
        mimeTypes.put(".shp",   "application/octet-stream");
        mimeTypes.put(".dbf",   "application/x-dbf");
        mimeTypes.put(".dcm",   "application/dicom");
        mimeTypes.put(".rar",   "application/x-rar-compressed");
        mimeTypes.put(".djvu",  "image/vnd.djvu");
        mimeTypes.put(".mobi",  "application/x-mobipocket-ebook");
        mimeTypes.put(".lit",   "application/x-ms-reader");
        mimeTypes.put(".bpg",   "image/bpg");
        mimeTypes.put(".sqlite","application/x-sqlite3");
        mimeTypes.put(".dwg",   "image/vnd.dwg");
        mimeTypes.put(".nes",   "application/vnd.nintendo.snes.rom");
        mimeTypes.put(".lnk",   "application/x-ms-shortcut");
        mimeTypes.put(".macho", "application/x-mach-binary");
        mimeTypes.put(".qcp",   "audio/qcelp");
        mimeTypes.put(".icns",  "image/x-icns");
        mimeTypes.put(".heic",  "image/heic");
        mimeTypes.put(".heif",  "image/heif");
        mimeTypes.put(".hdr",   "image/vnd.radiance");
        mimeTypes.put(".mrc",   "application/marc");
        mimeTypes.put(".mdb",   "application/x-msaccess");
        mimeTypes.put(".accdb", "application/x-msaccess");
        mimeTypes.put(".zst",   "application/zstd");
        mimeTypes.put(".cab",   "application/vnd.ms-cab-compressed");
        mimeTypes.put(".rpm",   "application/x-rpm");
        mimeTypes.put(".xz",    "application/x-xz");
        mimeTypes.put(".lz",    "application/lzip");
        mimeTypes.put(".torrent","application/x-bittorrent");
        mimeTypes.put(".cpio",  "application/x-cpio");
        mimeTypes.put(".xcf",   "image/x-xcf");
        mimeTypes.put(".pat",   "image/x-gimp-pat");
        mimeTypes.put(".gbr",   "image/x-gimp-gbr");
        mimeTypes.put(".glb",   "model/gltf-binary");
        mimeTypes.put(".txt",   "text/plain");
        mimeTypes.put(".html",  "text/html");
        mimeTypes.put(".svg",   "image/svg+xml");
        mimeTypes.put(".xml",   "text/xml");
        mimeTypes.put(".rss",   "application/rss+xml");
        mimeTypes.put(".atom",  "application/atom+xml");
        mimeTypes.put(".x3d",   "model/x3d+xml");
        mimeTypes.put(".kml",   "application/vnd.google-earth.kml+xml");
        mimeTypes.put(".xlf",   "application/x-xliff+xml");
        mimeTypes.put(".dae",   "model/vnd.collada+xml");
        mimeTypes.put(".gml",   "application/gml+xml");
        mimeTypes.put(".gpx",   "application/gpx+xml");
        mimeTypes.put(".tcx",   "application/vnd.garmin.tcx+xml");
        mimeTypes.put(".amf",   "application/x-amf");
        mimeTypes.put(".3mf",   "application/vnd.ms-package.3dmanufacturing-3dmodel+xml");
        mimeTypes.put(".xfdf",  "application/vnd.adobe.xfdf");
        mimeTypes.put(".owl",   "application/owl+xml");
        mimeTypes.put(".php",   "text/x-php");
        mimeTypes.put(".js",    "application/javascript");
        mimeTypes.put(".lua",   "text/x-lua");
        mimeTypes.put(".pl",    "text/x-perl");
        mimeTypes.put(".py",    "application/x-python");
        mimeTypes.put(".json",  "application/json");
        mimeTypes.put(".geojson","application/geo+json");
        mimeTypes.put(".ndjson","application/x-ndjson");
        mimeTypes.put(".rtf",   "text/rtf");
        mimeTypes.put(".tcl",   "text/x-tcl");
        mimeTypes.put(".csv",   "text/csv");
        mimeTypes.put(".tsv",   "text/tab-separated-values");
        mimeTypes.put(".vcf",   "text/vcard");
        mimeTypes.put(".ics",   "text/calendar");
        mimeTypes.put(".warc",  "application/warc");
    }

    public String getMIMEType(String filename) {
        for (String suffix : mimeTypes.keySet()) {
            if (filename.toLowerCase().endsWith(suffix)) return mimeTypes.get(suffix);
        }
        return "";
    }
}
