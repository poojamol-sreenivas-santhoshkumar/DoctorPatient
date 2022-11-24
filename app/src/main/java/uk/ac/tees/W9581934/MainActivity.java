package uk.ac.tees.W9581934;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import uk.ac.tees.W9581934.databinding.ActivityMainBinding;
import org.jetbrains.annotations.Nullable;


public final class MainActivity extends AppCompatActivity {

ActivityMainBinding binding;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}

/**
 * MD5: 61:55:AE:63:CD:D1:34:1E:C2:C3:17:6C:84:2D:D3:D9
 * SHA1: DB:EB:79:F4:BB:86:51:DA:AE:01:A6:50:B9:5F:E5:2C:50:19:79:BF
 * SHA-256: E2:9F:45:9C:B8:CF:86:A4:1B:38:34:23:32:82:B5:5F:D4:02:E7:97:96:66:5F:CB:B2:0D:57:D3:90:90:3C:7C
 */