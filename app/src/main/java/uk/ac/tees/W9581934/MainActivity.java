package uk.ac.tees.W9581934;


import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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