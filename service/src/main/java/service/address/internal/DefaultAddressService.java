package service.address.internal;

import model.adress.Address;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AddressRepository;
import service.address.AddressService;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.List;

/*
 */
@Service
@Named("defaultAddressService")
public class DefaultAddressService implements AddressService, Serializable {

    @Inject
    private AddressRepository repo;


    @Override
    public List<Address> getAll(){
        List<Address> list = repo.getAll();
        return list; 
    }

    private void saveToFile(List<Address> list){
        String text = "prename,name,mailAddress,web,mail2;";

        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("csv.txt"), "utf-8"));
            output.write(text);
            for(Address item : list){
                output.write(item.getPrename() + "," + item.getName() + "," + item.getMailAddress() + "," + item.getWeb() + "," + item.getMail2() + ";\n");
            }
            output.close();
            System.out.println("file created");
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public InputStream createExportStream(){
        String text = "prename,name,mailAddress,web,mail2;\n";
        StringBuilder sb = new StringBuilder();
        try {
            List<Address> list = getAll();
            sb.append(text);
            for(Address item : list){
                sb.append(item.getPrename() + "," + item.getName() + "," + item.getMailAddress() + "," + item.getWeb() + "," + item.getMail2() + ";\n");
            }
            return IOUtils.toInputStream(sb.toString(), "UTF-8");
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(Address dto) {
        repo.store(dto);
    }

    @Override
    public void delete(Long id) {
        Address entity = repo.findById(id);
        if(entity != null){
            repo.remove(entity);
        }
    }
}
