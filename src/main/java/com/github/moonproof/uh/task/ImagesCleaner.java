package com.github.moonproof.uh.task;

import com.github.moonproof.uh.domain.User;
import com.github.moonproof.uh.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImagesCleaner {

    @Value("${image.upload.path}")
    private String dirPath;

    private final UserRepo userRepo;

    public ImagesCleaner(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private List<File> listForDeletion = new ArrayList<>();

    @Scheduled(fixedRateString = "${image.deletion:60000}")
    public void clearUnusedImages() {
        File[] filesArr = new File(dirPath).listFiles();
        if (filesArr == null) {
            return;
        }

        List<String> dbUrls = userRepo.findUsersByUrlPictureIsNotNull()
                .stream()
                .map(User::getUrlPicture)
                .collect(Collectors.toList());

        if (!listForDeletion.isEmpty()) {
            listForDeletion.stream()
                    .filter(f -> !dbUrls.contains(f.getAbsolutePath()))
                    .forEach(File::delete);
        }

        List<String> filePaths = Arrays.stream(filesArr)
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        dbUrls.forEach(filePaths::remove);

        listForDeletion = filePaths.stream()
                .map(File::new)
                .filter(File::exists)
                .collect(Collectors.toList());
    }
}
