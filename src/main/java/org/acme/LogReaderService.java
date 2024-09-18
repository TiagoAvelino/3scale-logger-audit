// package org.acme;

// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.List;

// import org.acme.audits.AuditableDataRepository;
// import org.acme.audits.Audits;
// import org.jboss.logging.Logger;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// import io.quarkus.scheduler.Scheduled;
// import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Inject;

// @ApplicationScoped
// public class LogReaderService {

// @Inject
// AuditableDataRepository repository;

// private ObjectMapper objectMapper = new ObjectMapper(); // Jackson
// ObjectMapper

// private static final Logger LOG = Logger.getLogger(LogReaderService.class);

// // Define the path to the file that will store the lastReadId
// private static final String LAST_READ_ID_FILE = "mnt/last_read_id.txt";
// private Long lastReadId;

// public LogReaderService() {
// ensureDirectoryExists();
// this.lastReadId = loadLastReadIdFromFile();
// this.objectMapper = new ObjectMapper();
// this.objectMapper.registerModule(new JavaTimeModule()); // Register the
// module
// }

// private void ensureDirectoryExists() {
// File directory = new File("mnt");
// if (!directory.exists()) {
// if (directory.mkdirs()) {
// LOG.info("Directory 'mnt' created successfully.");
// } else {
// LOG.error("Failed to create 'mnt' directory.");
// }
// }
// }

// private Long loadLastReadIdFromFile() {
// File file = new File(LAST_READ_ID_FILE);
// if (file.exists()) {
// try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
// String line = reader.readLine();
// if (line != null) {
// return Long.parseLong(line);
// }
// } catch (IOException e) {
// LOG.error("Error reading last read ID from file", e);
// }
// }
// return 0L; // Default to 0 if the file doesn't exist or can't be read
// }

// private void saveLastReadIdToFile(Long lastReadId) {
// try (BufferedWriter writer = new BufferedWriter(new
// FileWriter(LAST_READ_ID_FILE))) {
// writer.write(lastReadId.toString());
// } catch (IOException e) {
// LOG.error("Error writing last read ID to file", e);
// }
// }

// // This method is triggered at application startup and scheduled every 2
// minutes

// public void checkAndReadLogs() {
// this.lastReadId = loadLastReadIdFromFile();
// List<Audits> newLogs = repository.find("id > ?1", lastReadId).list();
// if (!newLogs.isEmpty()) {
// for (Audits log : newLogs) {
// try {
// // Convert the Audits object to JSON string
// String logJson = objectMapper.writeValueAsString(log);
// System.out.println(logJson);
// // LOG.info("Reading log: " + logJson);
// } catch (IOException e) {
// LOG.error("Error converting log to JSON", e);
// }
// // Update the lastReadId to the ID of the current log
// lastReadId = log.getId();
// }
// // Save the lastReadId to the file after processing
// saveLastReadIdToFile(lastReadId);
// }
// }

// @Scheduled(every = "2m")
// void scheduledLogCheck() {
// checkAndReadLogs(); // Reuse the same method for scheduled execution
// }
// }
