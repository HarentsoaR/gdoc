package mg.md2i.gedi.viewmodel.dto;

import mg.md2i.gedi.enums.DocumentValidationEtat;

/**
 * Simple DTO pour alimenter les filtres d'état dans les pages de validation/suivi.
 */
public class DocumentEtatFilterOption {
    private final DocumentValidationEtat etat;
    private final String label;

    public DocumentEtatFilterOption(DocumentValidationEtat etat, String label) {
        this.etat = etat;
        this.label = label;
    }

    public DocumentValidationEtat getEtat() {
        return etat;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
