plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.2.x"

stonecutter parameters {
    swaps["mod_version"] = "\"${property("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version}\";"
    constants["release"] = property("mod.id") != "template"
    dependencies["fapi"] = node.project.property("deps.fabric_api") as String

    replacements {
        string(current.parsed < "1.21.11") {
            replace("Identifier", "ResourceLocation")
        }
        string(current.parsed < "1.17") {
            replace("MouseHandler", "MouseHelper")
        }
    }
}
