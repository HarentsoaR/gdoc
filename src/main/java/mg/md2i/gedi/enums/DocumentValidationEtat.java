package mg.md2i.gedi.enums;

/**
 * Centralise les différents états possibles pour un document de concours.
 * 0 = Rejeté, 1 = Validé, 2 = En cours de validation.
 */
public enum DocumentValidationEtat {
    REJETE(0, "Rejeté", "status-danger"),
    VALIDE(1, "Validé", "status-success"),
    EN_COURS(2, "En cours", "status-warning");

    private final int code;
    private final String label;
    private final String chipSclass;

    DocumentValidationEtat(int code, String label, String chipSclass) {
        this.code = code;
        this.label = label;
        this.chipSclass = chipSclass;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public String getChipSclass() {
        return chipSclass;
    }

    public static DocumentValidationEtat fromCode(Integer code) {
        if (code == null) {
            return EN_COURS;
        }
        for (DocumentValidationEtat etat : values()) {
            if (etat.code == code) {
                return etat;
            }
        }
        return EN_COURS;
    }
}
