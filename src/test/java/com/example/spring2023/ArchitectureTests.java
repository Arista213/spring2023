package com.example.spring2023;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTests {
    public static final String ROOT_PACKAGE_NAME = "com.example.spring2023";
    public static final JavaClasses CLASSES = new ClassFileImporter().importPackages(ROOT_PACKAGE_NAME);

    @Test
    public void Test() {

        layeredArchitecture().consideringAllDependencies()
                .layer("domain").definedBy(ROOT_PACKAGE_NAME + ".domain..")
                .layer("app").definedBy(ROOT_PACKAGE_NAME + ".app..")
                .layer("extern").definedBy(ROOT_PACKAGE_NAME + ".extern..")
                .whereLayer("extern").mayOnlyBeAccessedByLayers("extern")
                .whereLayer("app").mayOnlyBeAccessedByLayers("app", "extern")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("domain", "app", "extern")
                .check(CLASSES);
    }
}
