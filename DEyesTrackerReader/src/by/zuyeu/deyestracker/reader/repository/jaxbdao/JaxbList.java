/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.zuyeu.deyestracker.reader.repository.jaxbdao;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Fieryphoenix
 * @param <T>
 */
@XmlRootElement(name = "List")
public class JaxbList<T extends Serializable> {

    protected List<T> list;

    public JaxbList() {
    }

    public JaxbList(List<T> list) {
        this.list = list;
    }

    @XmlElement(name = "Item")
    public List<T> getList() {
        return list;
    }
}
