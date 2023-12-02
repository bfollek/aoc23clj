def part_1(file_path: str) -> int:
    return max(_elf_totals(file_path))


def part_2(file_path: str) -> int:
    totals = sorted(_elf_totals(file_path), reverse=True)
    return sum(totals[:3])


def _elf_totals(file_path: str) -> list[int]:
    rv = []
    total = 0
    with open(file_path) as f:
        lines = map(str.strip, f.readlines())
        for line in lines:
            if (not line) and total:
                rv.append(total)
                total = 0
                continue
            total += int(line)
    # Any `total` in progress when we run out of lines?
    if total > 0:
        rv.append(total)
    return rv
