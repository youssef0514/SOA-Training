package graphql;

// Import the business logic classes responsible for managing "UniteEnseignement" and "Module" data
import business.ModuleBusiness;
import business.UniteEnseignementBusiness;

// Import the GraphQL library class used to define the root of GraphQL mutations
import com.coxautodev.graphql.tools.GraphQLRootResolver;

// Import the entity classes
import entities.Module;
import entities.UniteEnseignement;

/**
 * This class defines all GraphQL mutations related to the "UniteEnseignement" and "Module" entities.
 * A mutation in GraphQL is used to perform write operations (create, update, delete)
 * as opposed to queries, which are for read operations.
 */
public class MutationResolver implements GraphQLRootResolver {

    // Attributes used to access the business logic (service layer)
    public UniteEnseignementBusiness helper;
    public ModuleBusiness moduleHelper;

    /**
     * Constructor of the resolver.
     * It initializes the business helpers that manage teaching units and modules.
     */
    public MutationResolver() {
        helper = new UniteEnseignementBusiness();
        moduleHelper = new ModuleBusiness();
    }

    /**
     * GraphQL Mutation: addUniteEnseignement
     *
     * This method allows clients to add a new "Unité d’Enseignement" to the system.
     *
     * When a GraphQL mutation like the following is executed:
     *
     * mutation {
     *     addUniteEnseignement(
     *         code: 101,
     *         domaine: "Informatique",
     *         responsable: "Dr. Smith",
     *         credits: 4,
     *         semestre: 2
     *     )
     * }
     *
     * GraphQL calls this Java method to create and save a new teaching unit.
     */
    public boolean addUniteEnseignement(int code, String domaine, String responsable, int credits, int semestre) {
        // Create a new UniteEnseignement object with the provided parameters
        UniteEnseignement ue = new UniteEnseignement(code, domaine, responsable, credits, semestre);

        // Call the business logic method to add the new unit
        // The helper handles the actual persistence or logic of adding the entity
        return helper.addUniteEnseignement(ue);
    }

    // 2. Création d’un nouveau module avec un type de retour Boolean
    public boolean addModule(String matricule, String libelle, int coef, int volumeHoraire, int codeUE, Module.TypeModule type) {
        UniteEnseignement ue = helper.getUEByCode(codeUE);
        if (ue == null) {
            ue = new UniteEnseignement(codeUE, null, null, 0, 0);
        }
        Module module = new Module(matricule, libelle, coef, volumeHoraire, type, ue);
        return moduleHelper.addModule(module);
    }

    // 3. Modification d’un module ayant un identifiant spécifique
    public Module updateModule(String matricule, String libelle, int coef, int volumeHoraire, Module.TypeModule type, int codeUE) {
        UniteEnseignement ue = helper.getUEByCode(codeUE);
        Module updatedModule = new Module(matricule, libelle, coef, volumeHoraire, type, ue);
        if (moduleHelper.updateModule(matricule, updatedModule)) {
            return updatedModule;
        }
        return null;
    }

    // 6. Suppression d'un module ayant une matricule spécifique
    public String deleteModule(String matricule) {
        if (moduleHelper.deleteModule(matricule)) {
            return "deleted successfully";
        }
        return "Module not found";
    }
}
