from xml.etree import ElementTree


class XmlDictConfig(dict):
    """
    taken from https://stackoverflow.com/a/39539757/1057820
    Note: need to add a root into if no exising
    Example usage:
    >>> tree = ElementTree.parse('your_file.xml')
    >>> root = tree.getroot()
    >>> xmldict = XmlDictConfig(root)
    Or, if you want to use an XML string:
    >>> root = ElementTree.XML(xml_string)
    >>> xmldict = XmlDictConfig(root)
    And then use xmldict for what it is... a dict.
    """

    def __init__(self, parent_element):
        super().__init__()
        if parent_element.items():
            self.update_shim(dict(parent_element.items()))
        for element in parent_element:
            if len(element):
                aDict = XmlDictConfig(element)
                #   if element.items():
                #   aDict.updateShim(dict(element.items()))
                self.update_shim({element.tag: aDict})
            elif element.items():  # items() is specially for attributes
                elementattrib = element.items()
                if element.text:
                    elementattrib.append((element.tag, element.text))  # add tag:text if there exist
                self.update_shim({element.tag: dict(elementattrib)})
            else:
                self.update_shim({element.tag: element.text})

    def update_shim(self, a_dict):
        for key in a_dict.keys():  # keys() includes tag and attributes
            if key in self:
                value = self.pop(key)
                if type(value) is not list:
                    listOfDicts = [value, a_dict[key]]
                    self.update({key: listOfDicts})
                else:
                    value.append(a_dict[key])
                    self.update({key: value})
            else:
                self.update({key: a_dict[key]})  # it was self.update(aDict)
