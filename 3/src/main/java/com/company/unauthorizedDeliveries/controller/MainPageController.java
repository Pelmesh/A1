package com.company.unauthorizedDeliveries.controller;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.company.unauthorizedDeliveries.Repo.LoginsRepo;
import com.company.unauthorizedDeliveries.Repo.PostingsRepo;
import com.company.unauthorizedDeliveries.domain.Logins;
import com.company.unauthorizedDeliveries.domain.Postings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class MainPageController {
    @Autowired
    public LoginsRepo loginRepo;
    @Autowired
    public PostingsRepo postingsRepo;

    @GetMapping
    public String ParsFileController(Model model) throws IOException {
        loginRepo.deleteAll();
        parseFileLogins();
        postingsRepo.deleteAll();
        parseFilePostings();
        model.addAttribute("logins", loginRepo.findAll());
        model.addAttribute("postings", postingsRepo.findAll());
        return "main";
    }

    @GetMapping(value = "api")
    @ResponseBody
    public List<Postings> getJSON(@RequestParam(defaultValue = "") String dateStart,
                                  @RequestParam(defaultValue = "") String dateEnd,
                                  @RequestParam(defaultValue = "") String select) throws IOException {
        loginRepo.deleteAll();
        parseFileLogins();
        postingsRepo.deleteAll();
        parseFilePostings();
        return searchData(dateStart, dateEnd, select);
    }

    @PostMapping
    public String sort(@RequestParam String dateStart,
                       @RequestParam String dateEnd,
                       @RequestParam String select,
                       Model model) {

        model.addAttribute("logins", loginRepo.findAll());
        model.addAttribute("postings", searchData(dateStart, dateEnd, select));
        return "main";
    }

    public List<Postings> searchData(String dateStart, String dateEnd, String select) {
        if (!dateStart.equals("") & dateEnd.equals("")) {
            if (select.equals("true") || select.equals("false")) {
                return postingsRepo.findAllByPstngDateAfterAndAuthorizedDelivery(LocalDate.parse(dateStart),
                        Boolean.parseBoolean(select));
            } else {
                return postingsRepo.findAllByPstngDateAfter(LocalDate.parse(dateStart));
            }
        }
        if (!dateEnd.equals("") & dateStart.equals("")) {
            if (select.equals("true") || select.equals("false")) {
                return postingsRepo.findAllByPstngDateBeforeAndAuthorizedDelivery(
                        LocalDate.parse(dateEnd),
                        Boolean.parseBoolean(select));
            } else {
                return postingsRepo.findAllByPstngDateBefore(LocalDate.parse(dateEnd));
            }
        }
        if (!dateEnd.equals("") & !dateStart.equals("")) {
            if (select.equals("true") || select.equals("false")) {
                return postingsRepo.findAllByPstngDateBetweenAndAuthorizedDelivery(
                        LocalDate.parse(dateStart),
                        LocalDate.parse(dateEnd),
                        Boolean.parseBoolean(select));
            } else {
                return postingsRepo.findAllByPstngDateBetween(
                        LocalDate.parse(dateStart),
                        LocalDate.parse(dateEnd));
            }
        }
        if (dateEnd.equals("") & dateStart.equals("")) {
            if (select.equals("true") || select.equals("false")) {
                return postingsRepo.findAllByAuthorizedDelivery(
                        Boolean.parseBoolean(select));
            } else {
                return postingsRepo.findAll();
            }
        }
        return postingsRepo.findAll();
    }


    private void parseFilePostings() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        CSVReader reader = new CSVReader(new FileReader("postings.csv"), ';', '"', 1);
        List<String[]> allRows = reader.readAll();
        for (String[] row : allRows) {
            String txt = Arrays.toString(row);
            txt = txt.substring(1, txt.length() - 1);
            System.out.println("txt: " + txt);
            if (!txt.equals("")) {
                Postings post = new Postings();
                String[] words = txt.split(", \t");
                post.setMatDoc(Long.parseLong(words[0]));
                post.setItem(Integer.parseInt(words[1]));
                post.setDocDate(LocalDate.parse(words[2], formatter));
                post.setPstngDate(LocalDate.parse(words[3], formatter));
                post.setMaterialDescription(words[4]);
                post.setQuantity(Integer.parseInt(words[5]));
                post.setbUn(words[6]);
                post.setAmountLC(Double.parseDouble(words[7].replace(",", ".")));
                post.setCrcy(words[8]);
                post.setUserName(words[9]);
                Logins logins = loginRepo.findAllByAppAccountNameAndActive(words[9], true);
                post.setAuthorizedDelivery(logins != null);
                postingsRepo.save(post);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void parseFileLogins() throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();
        String csvFilename = "logins.csv";
        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
        List list = csv.parse(setColumMapping(), csvReader);
        list.remove(0);
        for (Object object : list) {
            Logins login = (Logins) object;
            loginRepo.save(login);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Logins.class);
        String[] columns = new String[]{"application", "appAccountName", "isActive", "jobTitle", "department"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
