package graphql;

// Import the business logic classes responsible for managing "UniteEnseignement" and "Module" data
import business.ModuleBusiness;
import business.UniteEnseignementBusiness;

// Import the GraphQL library class used to define the root of GraphQL queries
import com.coxautodev.graphql.tools.GraphQLRootResolver;

// Import the entity classes
import entities.Module;
import entities.UniteEnseignement;

import java.util.List;

/**
 * This class acts as the root resolver for GraphQL queries.
 * It tells GraphQL how to fetch data when a query is executed.
 *
 * In this example, it handles queries related to "UniteEnseignement" and "Module".
 */
public class QueryResolver implements GraphQLRootResolver {

    // Attributes used to call the business logic layer (service) for data operations
    public UniteEnseignementBusiness helper;
    public ModuleBusiness moduleHelper;

    /**
     * Constructor of the resolver.
     * It initializes the business helpers that provide methods to retrieve UE and Module data.
     */
    public QueryResolver(){
        helper = new UniteEnseignementBusiness();
        moduleHelper = new ModuleBusiness();
    }

    /**
     * GraphQL Query: allUEs
     *
     * This method will be automatically exposed to the GraphQL schema as a query named "allUEs".
     *
     * When a client sends the GraphQL query:
     *
     *    query {
     *        allUEs {
     *            code
     *            domaine
     *        }
     *    }
     *
     * GraphQL will call this method to return a list of "UniteEnseignement" objects.
     *
     * @return List of all Teaching Units (Unité d’Enseignement)
     */
    public List<UniteEnseignement> allUEs(){
        // Delegates the request to the business layer method getListeUE()
        // which fetches all UE entities (e.g., from a database or a static list)
        return helper.getListeUE();
    }

    // 1. Récupération de la liste de tous les modules
    public List<Module> allModules() {
        return moduleHelper.getAllModules();
    }

    public List<Module> getAllModules() {
        return moduleHelper.getAllModules();
    }

    // 4. Récupération de la liste des modules ayant un type spécifique
    public List<Module> getModulesByType(Module.TypeModule type) {
        return moduleHelper.getModulesByType(type);
    }

    public List<Module> modulesByType(Module.TypeModule type) {
        return moduleHelper.getModulesByType(type);
    }

    // 5. Récupération d'un module ayant une matricule spécifique
    public Module getModuleByMatricule(String matricule) {
        return moduleHelper.getModuleByMatricule(matricule);
    }

    public Module moduleByMatricule(String matricule) {
        return moduleHelper.getModuleByMatricule(matricule);
    }

    // Récupération des modules d'une UE spécifique
    public List<Module> getModulesByUE(int codeUE) {
        UniteEnseignement ue = helper.getUEByCode(codeUE);
        if (ue == null) {
            ue = new UniteEnseignement(codeUE, null, null, 0, 0);
        }
        return moduleHelper.getModulesByUE(ue);
    }
}
