package com.microblink.blinkid.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.microblink.blinkid.core.BlinkIdSdkSettings;
import com.microblink.blinkid.core.result.AlphabetType;
import com.microblink.blinkid.core.session.BlinkIdSessionSettings;
import com.microblink.blinkid.ux.contract.BlinkIdScanActivitySettings;
import com.microblink.blinkid.ux.contract.MbBlinkIdScan;
import com.microblink.blinkid.ux.settings.BlinkIdUxSettings;
import com.microblink.blinkid.ux.camera.CameraSettings;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_java);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String licenseKey = "sRwCAB1jb20ubWljcm9ibGluay5ibGlua2lkLnNhbXBsZQBsZXlKRGNtVmhkR1ZrVDI0aU9qRTNOREU0T0RBME9UVTBPRE1zSWtOeVpXRjBaV1JHYjNJaU9pSmtaR1F3TmpabFppMDFPREl6TFRRd01EZ3RPVFE0TUMwMU5EVTRZakF4WVRVMllqZ2lmUT09itFq81+BsXnPNx0/n2rf2kwiIQOLqbsCQB6gmKLNoCVHc8KwvGtkduMoga0UFeWF2YLmXKc+PmeqU8Ax6+3MmuIWeZhzBYngmhERJF1p5avA0arq4HmYVJZwSfnw";
        BlinkIdSdkSettings sdkSettings = new BlinkIdSdkSettings(licenseKey);
        CameraSettings cameraSettings = new CameraSettings();
        BlinkIdSessionSettings sessionSettings = new BlinkIdSessionSettings();
        BlinkIdUxSettings uxSettings = new BlinkIdUxSettings(15000, 10000);
        BlinkIdScanActivitySettings activitySettings = new BlinkIdScanActivitySettings(sdkSettings, sessionSettings, uxSettings, cameraSettings);

        ActivityResultLauncher<BlinkIdScanActivitySettings> resultLauncher = registerForActivityResult(
                new MbBlinkIdScan(),
                result -> {
                    // handle result here
                    String text;
                    if (result.getResult() != null) {
                        text = result.getResult().getLastName().value(AlphabetType.Latin);
                    } else {
                        text = "Scan unsuccessful!";
                    }

                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                }
        );

        findViewById(R.id.button).setOnClickListener(v -> {
            resultLauncher.launch(activitySettings);
        });
    }
}