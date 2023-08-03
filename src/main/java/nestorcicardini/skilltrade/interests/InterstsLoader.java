package nestorcicardini.skilltrade.interests;

import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Component
public class InterstsLoader implements CommandLineRunner {

	@Autowired
	InterestRepository interestRepo;

	@Override
	public void run(String... args) throws Exception {

		if (interestRepo.findAll().isEmpty()) {

//			String filePath = "src/main/java/nestorcicardini/skilltrade/interests/Interests.csv";
			String filePath = "https://raw.githubusercontent.com/Nestor162/SkillTrade-Captsone-Project-Backend/main/src/main/java/nestorcicardini/skilltrade/interests/Interests.csv";

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

					Interest currentInterest = new Interest(values[0].trim());
					interestRepo.save(currentInterest);

				}
			} finally {
				if (csvReader != null)
					csvReader.close(); // Close the CSVReader
				System.out.println(
						"-- CSV interests data loaded successfully into the database. CSV file closed. --");

			}
		} else

		{
			System.out.println(
					"----- Interests database already populated -----");
		}
	}

}
