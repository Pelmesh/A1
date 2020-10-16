package com.company.unauthorizedDeliveries.controller;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.company.unauthorizedDeliveries.Repo.LoginsRepo;
import com.company.unauthorizedDeliveries.Repo.PostingsRepo;
import com.company.unauthorizedDeliveries.domein.Logins;
import com.company.unauthorizedDeliveries.domein.Postings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private LoginsRepo loginRepo;
    @Autowired
    private PostingsRepo postingsRepo;

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

    @PostMapping
    public String sort(@RequestParam String dateStart,
                       @RequestParam String dateEnd,
                       @RequestParam String select,
                       Model model) {

        model.addAttribute("logins", loginRepo.findAll());
        if (!dateStart.equals("") & dateEnd.equals("")) {
            if (!select.equals("Авторизованная поставка?")) {
                model.addAttribute("postings", postingsRepo.findAllByPstngDateAfter(LocalDate.parse(dateStart)));
                return "main";
            } else {
                model.addAttribute("postings", postingsRepo.findAllByPstngDateAfter(LocalDate.parse(dateStart)));
                return "main";
            }
        }
        if (!dateEnd.equals("") & dateStart.equals("")) {
            if (!select.equals("Авторизованная поставка?")) {
                model.addAttribute("postings", postingsRepo.findAllByPstngDateBeforeAndAuthorizedDelivery(
                        LocalDate.parse(dateEnd),
                        Boolean.parseBoolean(select)));
                return "main";
            } else {
                model.addAttribute("postings", postingsRepo.findAllByPstngDateBefore(LocalDate.parse(dateEnd)));
                return "main";
            }
        }
        if (!dateEnd.equals("") & !dateStart.equals("")) {
            if (!select.equals("Авторизованная поставка?")) {
                model.addAttribute("postings", postingsRepo.findAllByPstngDateBetweenAndAuthorizedDelivery(
                        LocalDate.parse(dateStart),
                        LocalDate.parse(dateEnd),
                        Boolean.parseBoolean(select)));
                return "main";
            } else {
                model.addAttribute("postings", postingsRepo.findAllByPstngDateBetween(
                        LocalDate.parse(dateStart),
                        LocalDate.parse(dateEnd)));
                return "main";
            }
        }
        if (dateEnd.equals("") & dateStart.equals("")) {
            if (!select.equals("Авторизованная поставка?")) {
                model.addAttribute("postings", postingsRepo.findAllByAuthorizedDelivery(
                        Boolean.parseBoolean(select)));
                return "main";
            } else {
                model.addAttribute("postings", postingsRepo.findAll());
                return "main";
            }
        }
        return "main";
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
