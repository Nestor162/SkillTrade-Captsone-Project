package nestorcicardini.skilltrade.languages;

import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Component
public class LanguagesLoader implements CommandLineRunner {

	@Autowired
	LanguageRepository langRepo;

	@Override
	public void run(String... args) throws Exception {

		if (langRepo.findAll().isEmpty()) {

//			String filePath = "src/main/java/nestorcicardini/skilltrade/languages/Languages.csv";
			String filePath = "https://raw.githubusercontent.com/Nestor162/SkillTrade-Captsone-Project-Backend/main/src/main/java/nestorcicardini/skilltrade/languages/Languages.csv";

			CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath))
					.build();

			try (csvReader) {
				String[] values = null;
				boolean firstLine = true;

				while ((values = csvReader.readNext()) != null) {

					if (firstLine) {
						firstLine = false;
						continue; // Skip the first line
					}

					Language currentLang = new Language(values[3].trim(),
							values[0].trim());
					langRepo.save(currentLang);

				}
			} finally {
				if (csvReader != null)
					csvReader.close(); // Close the CSVReader
				System.out.println(
						"-- CSV languages data loaded successfully into the database. CSV file closed. --");

			}
		} else

		{
			System.out
					.println("----- Language database already populated -----");
		}
	}

}
