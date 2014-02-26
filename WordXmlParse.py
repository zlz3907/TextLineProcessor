#! /usr/bin/python
# -*- coding: utf-8 -*-

import re;
import sys;
import os;

textre = re.compile("\!\[CDATA\[(.*?)\]\]", re.DOTALL);
def get_text(xml):
    match = re.search(textre, xml);
    if not match:
        return xml;
    return match.group(1);

def get_elements(xml, elem):
    p = re.compile("<" + elem + ">" + "(.*?)</" + elem + ">", re.DOTALL);
    it = p.finditer(xml);
    result = [];
    for m in it:
        result.append(m.group(1));
    return result;

def get_elements_by_path(xml, elem):
    if type(xml) == type(''):
        xml = [xml];
    if type(elem) == type(''):
        elem = elem.split('/');
    if (len(xml) == 0):
        return [];
    elif (len(elem) == 0):
        return xml;
    elif (len(elem) == 1):
        result = [];
        for item in xml:
            result += get_elements(item, elem[0]);
        return result;
    else:
        subitems = [];
        for item in xml:
            subitems += get_elements(item, elem[0]);
        return get_elements_by_path(subitems, elem[1:]);

def write_xml_tocache(xml, path):
    afile = open(path, 'w');
    afile.write(xml);
    afile.close();
str_list = [];
def process(path):
    xmlfile = open(path);
    xml = xmlfile.read();
    original_query = get_elements(xml, "query");
    queryword = get_text(original_query[0]);
    custom_translations = get_elements(xml, "basic");
    translated = False;
    el_phonetic = get_elements(xml, "phonetic");
    phonetic_symbol = " ";
    if not len(el_phonetic) <= 0:
        phonetic_symbol = " [" + get_text(get_elements(xml, "phonetic")[0]) + "] ";
    paragraph_symbol = get_text(get_elements(xml, "paragraph")[0]);
    str_arow = "<item><word><![CDATA[" + queryword + "]]></word><trans><![CDATA[" + paragraph_symbol + " ";
    arow_trans = "";
    for cus in custom_translations:
        source = "youdao:"; #get_elements_by_path(cus, "basic/explains");
        contents = get_elements_by_path(cus, "explains/ex");
        str_trans = [];
        for content in contents[0:5]:
            str_trans.append(get_text(content));
        translated = True;
        arow_trans = " ".join(str_trans);
    arow = str_arow + arow_trans + "]]></trans><phonetic><![CDATA[" + phonetic_symbol + "]]></phonetic><tags></tags><progress>0</progress></item>\n";
    str_list.append(arow);
    print arow;
    xmlfile.close();

def main(argv):
    if len(argv) <= 1:
        sys.exit(1);
    word_folder = argv[0];
    print word_folder;
    if os.path.exists(word_folder):
        for f in os.listdir(word_folder):
            process(os.path.join(word_folder, f));
    write_xml_tocache("<wordbook>\n" + "".join(str_list) + "\n</wordbook>\n", argv[1]);
if __name__ == "__main__":
    main(sys.argv[1:]);
